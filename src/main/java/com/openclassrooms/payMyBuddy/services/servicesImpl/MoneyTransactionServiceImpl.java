package com.openclassrooms.payMyBuddy.services.servicesImpl;

import com.openclassrooms.payMyBuddy.dao.BalanceDao;
import com.openclassrooms.payMyBuddy.dao.MoneyTransactionDao;
import com.openclassrooms.payMyBuddy.model.Balance;
import com.openclassrooms.payMyBuddy.model.MoneyTransaction;
import com.openclassrooms.payMyBuddy.model.Payment;
import com.openclassrooms.payMyBuddy.services.MoneyTransactionService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@AllArgsConstructor
@Service
public class MoneyTransactionServiceImpl implements MoneyTransactionService {
    public static double FEE = 0.005;
    private final MoneyTransactionDao moneyTransactionDao;
    private final BalanceDao balanceDao;

    @Override
    public MoneyTransaction sendMoney(MoneyTransaction moneyTransaction) {
        //calculate fee
        moneyTransaction.getPayment().setFee(calculateFee(moneyTransaction.getPayment().getAmount()));

        Optional<Balance> clientBalance = balanceDao.findById(moneyTransaction.getSenderClientId());
        Optional<Balance> friendBalance = balanceDao.findById(moneyTransaction.getReceiverClientId());

        //assure the client has enough money
        if (isValidClients(clientBalance, friendBalance) && isBalanceAvailableByPayment(clientBalance, moneyTransaction)) {
            clientBalance.get().setAmount(clientBalance.get().getAmount() - (moneyTransaction.getPayment().getAmount() + moneyTransaction.getPayment().getFee()));
            friendBalance.get().setAmount(friendBalance.get().getAmount() + moneyTransaction.getPayment().getAmount());
            moneyTransaction.setMoneyTransactionTimestamp(LocalDateTime.now());
            balanceDao.saveAll(List.of(clientBalance.get(), friendBalance.get()));
            Payment payment = moneyTransaction.getPayment();
            moneyTransaction.setPayment(null);
            moneyTransaction = moneyTransactionDao.save(moneyTransaction);
            payment.setTransactionId(moneyTransaction.getId());
            moneyTransaction.setPayment(payment);
            moneyTransaction.getPayment().setTransactionId(moneyTransaction.getId());
            return moneyTransactionDao.save(moneyTransaction);
        } else {
            log.info("Please feed your balance, minimum amount is{} ", moneyTransaction.getPayment().getAmount() + moneyTransaction.getPayment().getFee());
            return null;
        }
    }

    private boolean isValidClients(Optional<Balance> clientBalance, Optional<Balance> friendBalance) {
        return clientBalance.isPresent() && friendBalance.isPresent();
    }

    private double calculateFee(double amount) {
        return amount * FEE;
    }

    private boolean isBalanceAvailableByPayment(Optional<Balance> clientBalance, MoneyTransaction moneyTransaction) {
        return (clientBalance.isPresent()
                && (clientBalance.get().getAmount() >= moneyTransaction.getPayment().getAmount() + moneyTransaction.getPayment().getFee()));
    }


}
