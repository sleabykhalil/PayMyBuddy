package com.openclassrooms.payMyBuddy.services.servicesImpl;

import com.openclassrooms.payMyBuddy.dao.BalanceDao;
import com.openclassrooms.payMyBuddy.dao.ClientDao;
import com.openclassrooms.payMyBuddy.dao.MoneyTransactionDao;
import com.openclassrooms.payMyBuddy.dto.MoneyTransactionDto;
import com.openclassrooms.payMyBuddy.dto.PaymentDto;
import com.openclassrooms.payMyBuddy.dto.TransactionListDto;
import com.openclassrooms.payMyBuddy.dto.mapper.MoneyTransactionMapper;
import com.openclassrooms.payMyBuddy.model.Balance;
import com.openclassrooms.payMyBuddy.model.Client;
import com.openclassrooms.payMyBuddy.model.MoneyTransaction;
import com.openclassrooms.payMyBuddy.model.Payment;
import com.openclassrooms.payMyBuddy.services.MoneyTransactionService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@AllArgsConstructor
@Service
@Transactional
public class MoneyTransactionServiceImpl implements MoneyTransactionService {
    public static double FEE = 0.005; // fee 0.5%
    private final MoneyTransactionDao moneyTransactionDao;
    private final BalanceDao balanceDao;
    private final ClientDao clientDao;
    @Autowired
    MoneyTransactionMapper moneyTransactionMapper;

    /**
     * Send money by applying mony transaction
     *
     * @param moneyTransactionDto
     * @return
     */
    @Override
    public MoneyTransaction sendMoney(MoneyTransactionDto moneyTransactionDto) {
        //calculate fee
        MoneyTransaction moneyTransaction = moneyTransactionMapper.moneyTransActionDtoToMoneyTransaction(moneyTransactionDto);
        moneyTransaction.getPayment().setFee(calculateFee(moneyTransaction.getPayment().getAmount()));

        Optional<Balance> clientBalance = balanceDao.findById(moneyTransactionDto.getSenderClientId());
        Optional<Balance> friendBalance = balanceDao.findById(moneyTransactionDto.getReceiverClientId());

        //assure the client has enough money
        if (isValidClients(clientBalance, friendBalance)
                && isBalanceAvailableByPayment(clientBalance, moneyTransaction)) {
            clientBalance.get().setAmount(clientBalance.get().getAmount()
                    - (moneyTransaction.getPayment().getAmount() + moneyTransaction.getPayment().getFee()));
            friendBalance.get().setAmount(friendBalance.get().getAmount() + moneyTransaction.getPayment().getAmount());
            balanceDao.saveAll(List.of(clientBalance.get(), friendBalance.get()));

            moneyTransaction.setMoneyTransactionTimestamp(LocalDateTime.now());
            Payment payment = moneyTransaction.getPayment();
            moneyTransaction.setPayment(null);
            moneyTransaction = moneyTransactionDao.save(moneyTransaction);

            payment.setTransactionId(moneyTransaction.getId());
            moneyTransaction.setPayment(payment);
            moneyTransaction.getPayment().setTransactionId(moneyTransaction.getId());
            return moneyTransactionDao.save(moneyTransaction);
        } else {
            log.info("Please feed your balance, minimum amount is{} "
                    , moneyTransaction.getPayment().getAmount() + moneyTransaction.getPayment().getFee());
            return null;
        }
    }

    /**
     * Get List of transaction already made
     *
     * @param clientEmail
     * @param pageable
     * @return
     */
    @Override
    public TransactionListDto getTransactionList(String clientEmail, Pageable pageable) {
        Optional<Client> client = clientDao.findClientByEmailAccount(clientEmail);
        Page<MoneyTransaction> moneyTransactionList = client.isPresent()
                ? moneyTransactionDao.findBySenderClientIdOrderByMoneyTransactionTimestampDesc(client.get().getClientId(), pageable)
                : Page.empty();
        List<PaymentDto> paymentDtoList = new ArrayList<>();
        moneyTransactionList.forEach(x -> paymentDtoList.add(PaymentDto.builder()
                .receiverName(clientDao.findById(x.getReceiverClientId()).get().getFirstName())
                .motive(x.getPayment().getMotive())
                .amount(x.getPayment().getAmount())
                .build()));

        return TransactionListDto.builder()
                .paymentDtoList(paymentDtoList)
                .pageNumber(moneyTransactionList.getTotalPages()).build();
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
