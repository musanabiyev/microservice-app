package com.company.service;

import com.company.client.contract.AccountDto;
import com.company.entity.Account;
import com.company.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final ModelMapper modelMapper;

    public AccountDto get(String id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException());
        return modelMapper.map(account, AccountDto.class);
    }

    public AccountDto save(AccountDto accountDto) {
        Account account = modelMapper.map(accountDto, Account.class);
        account = accountRepository.save(account);
        accountDto.setId(account.getId());
        return accountDto;
    }

    public AccountDto update(String id, AccountDto accountDto) {
        Assert.isNull(id, "Id cannot be null");
        Optional<Account> account = accountRepository.findById(id);
        Account accountToUpdate = account.map(it -> {
            it.setDateOfBirth(accountDto.getBirthDate());
            it.setName(accountDto.getName());
            it.setSurname(accountDto.getSurname());
            return it;
        }).orElseThrow(IllegalArgumentException::new);
        return modelMapper.map(accountRepository.save(accountToUpdate), AccountDto.class);
    }

    public void delete(String id) {
        Account account = accountRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException());
        accountRepository.delete(account);
    }

    public List<AccountDto> getAll(Pageable pageable) {
        List<Account> Account = accountRepository.findAll();
        return null;
    }
}
