package com.queue.system.service;


import com.queue.system.dto.response.TicketResponse;
import com.queue.system.entity.*;
import com.queue.system.repository.QueueSessionRepository;
import com.queue.system.repository.ServiceRepository;
import com.queue.system.repository.TicketRepository;
import com.queue.system.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class QueueService {

    private final ServiceRepository serviceRepository;
    private final UserRepository userRepository;
    private final QueueSessionRepository queueSessionRepository;
    private final TicketRepository ticketRepository;

    public QueueService(ServiceRepository serviceRepository,
                        UserRepository userRepository,
                        QueueSessionRepository queueSessionRepository,
                        TicketRepository ticketRepository) {
        this.serviceRepository = serviceRepository;
        this.userRepository = userRepository;
        this.queueSessionRepository = queueSessionRepository;
        this.ticketRepository = ticketRepository;
    }

    @Transactional
    public TicketResponse takeTicket(Long serviceId, Long userId, String priority){
        com.queue.system.entity.Service service = serviceRepository.findById(serviceId)
                .orElseThrow(() -> new RuntimeException("Service tidak ditemukan"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User tidak ditemukan"));

        if (!service.getIsActive()) {
            throw new RuntimeException("Layanan sedang tidak aktif!");
        }
        LocalDate today = LocalDate.now();
        QueueSession session = queueSessionRepository
                .findByServiceAndLocalDate(service, today)
                .orElseGet(() -> createNewSession(service, today));

        Integer currentNumber = (session.getCurrentNumber() != null) ? session.getCurrentNumber() : 0;
        int nextPosition = currentNumber + 1;

        if (nextPosition > service.getMaxQueuePerDay()) {
            throw new RuntimeException("Kuota antrian hari ini sudah habis");
        }

        String queueNumber = generateQueueNumber(service.getPrefix(), nextPosition);

        Ticket ticket = new Ticket();
        ticket.setQueueSession(session);
        ticket.setUser(user);
        ticket.setQueueNumber(queueNumber);
        ticket.setPosition(nextPosition);
        Priority priorityEnum = Priority.REGULAR;
        if (priority != null && !priority.isEmpty()) {
            try {
                priorityEnum = Priority.valueOf(priority.toUpperCase());
            } catch (IllegalArgumentException e) {
                priorityEnum = Priority.REGULAR;
            }
        }
        ticket.setPriority(priorityEnum);
        ticket.setBookedAt(LocalDateTime.now());

        Ticket savedTicket = ticketRepository.save(ticket);

        session.setCurrentNumber(nextPosition);
        queueSessionRepository.save(session);

        int estimatedWait = calculateEstimatedWaitTime(service, session);

        TicketResponse response = new TicketResponse();
        response.setTicketId(savedTicket.getId());
        response.setQueueNumber(savedTicket.getQueueNumber());
        response.setServiceName(service.getServiceType().name());
        response.setBranchName(service.getBranch().getName());
        response.setPosition(savedTicket.getPosition());
        response.setEstimatedWaitMinutes(estimatedWait);
        response.setStatus("WAITING");
        response.setBookedAt(savedTicket.getBookedAt());

        return response;

    }

    private QueueSession createNewSession(com.queue.system.entity.Service service, LocalDate date) {
        QueueSession session = new QueueSession();
        session.setService(service);
        session.setLocalDate(date);
        session.setCurrentNumber(0);
        session.setTotalCalled(0);
        session.setStatus(QueueSessionStatus.valueOf("ACTIVE"));
        return queueSessionRepository.save(session);
    }

    private String generateQueueNumber(String prefix, int number) {
        return String.format("%s%03d", prefix, number);
    }

    private Integer calculateEstimatedWaitTime(com.queue.system.entity.Service service, QueueSession session) {
        int waitingCount = ticketRepository.countWaitingTickets(session.getId());
        return waitingCount * service.getAvgServiceMinutes();
    }


}
