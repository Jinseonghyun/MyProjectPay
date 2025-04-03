package com.backend.membership.application.service;

import com.backend.common.UseCase;
import com.backend.membership.adapter.out.persistence.MembershipJpaEntity;
import com.backend.membership.adapter.out.persistence.MembershipMapper;
import com.backend.membership.application.port.in.FindMembershipCommand;
import com.backend.membership.application.port.in.FindMembershipUseCase;
import com.backend.membership.application.port.out.FindMembershipPort;
import com.backend.membership.domain.Membership;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@Transactional
@RequiredArgsConstructor
public class FindMembershipService implements FindMembershipUseCase {

    private final FindMembershipPort findMembershipPort;
    private final MembershipMapper membershipMapper;

    @Override
    public Membership findMembership(FindMembershipCommand command) {
        MembershipJpaEntity entity = findMembershipPort.findMembership(new Membership.MembershipId(command.getMembershipId()));
        return membershipMapper.mapToDomainEntity(entity);
    }

}
