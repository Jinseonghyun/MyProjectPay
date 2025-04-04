package com.backend.membership.application.service;

import com.backend.common.UseCase;
import com.backend.membership.adapter.out.persistence.MembershipJpaEntity;
import com.backend.membership.adapter.out.persistence.MembershipMapper;
import com.backend.membership.application.port.in.RegisterMembershipCommand;
import com.backend.membership.application.port.in.RegisterMembershipUseCase;
import com.backend.membership.application.port.out.RegisterMembershipPort;
import com.backend.membership.domain.Membership;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@Transactional
@RequiredArgsConstructor
public class RegisterMembershipService implements RegisterMembershipUseCase {

    private final RegisterMembershipPort registerMembershipPort;
    private final MembershipMapper membershipMapper;

    @Override
    public Membership registerMembership(RegisterMembershipCommand command) {
        MembershipJpaEntity jpaEntity = registerMembershipPort.createMembership(
                new Membership.MembershipName(command.getName()),
                new Membership.MembershipEmail(command.getEmail()),
                new Membership.MembershipAddress(command.getAddress()),
                new Membership.MembershipIsValid(command.isValid()),
                new Membership.MembershipIsCorp(command.isCorp())
        );
        return membershipMapper.mapToDomainEntity(jpaEntity);
    }
}
