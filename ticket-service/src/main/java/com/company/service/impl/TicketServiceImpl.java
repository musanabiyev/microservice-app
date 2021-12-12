package com.company.service.impl;

import com.company.client.AccountServiceClient;
import com.company.client.contract.AccountDto;
import com.company.dto.TicketDto;
import com.company.model.PriorityType;
import com.company.model.Ticket;
import com.company.model.TicketStatus;
import com.company.repository.TicketRepository;
import com.company.service.TicketNotificationService;
import com.company.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;
    private final TicketNotificationService ticketNotificationService;
    private final AccountServiceClient accountServiceClient;

    @Override
    @Transactional
    public TicketDto save(TicketDto ticketDto) {

        ResponseEntity<AccountDto> accountDtoResponseEntity =
                accountServiceClient.get(ticketDto.getAssignee());

        if (ticketDto.getDescription() == null)
            throw new IllegalArgumentException("Description cannot be empty");

        Ticket ticket = new Ticket();

        ticket.setDescription(ticketDto.getDescription());
        ticket.setNotes(ticketDto.getNotes());
        ticket.setTicketDate(ticketDto.getTicketDate());
        ticket.setTicketStatus(TicketStatus.valueOf(ticketDto.getTicketStatus()));
        ticket.setPriorityType(PriorityType.valueOf(ticketDto.getPriorityType()));
        ticket.setAssignee(accountDtoResponseEntity.getBody().getId());

        ticket = ticketRepository.save(ticket);

        ticketDto.setId(ticket.getId());

        ticketNotificationService.sendToQueue(ticket);
        return ticketDto;
    }

    @Override
    public TicketDto update(String id, TicketDto ticketDto) {
        return null;
    }

    @Override
    public TicketDto getById(String ticketId) {
        return null;
    }

    @Override
    public Page<TicketDto> getPagination(Pageable pageable) {
        return null;
    }
}
