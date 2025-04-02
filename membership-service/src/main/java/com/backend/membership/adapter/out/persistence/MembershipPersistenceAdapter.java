package com.backend.membership.adapter.out.persistence;

import com.backend.membership.application.port.out.RegisterMembershipPort;
import com.backend.membership.domain.Membership;
import common.PersistenceAdapter;
import lombok.RequiredArgsConstructor;

@PersistenceAdapter
@RequiredArgsConstructor
public class MembershipPersistenceAdapter implements RegisterMembershipPort {

    private final SpringDataMembershipRepository membershipRepository;

    @Override
    public MembershipJpaEntity createMembership(Membership.MembershipName membershipName, Membership.MembershipEmail membershipEmail, Membership.MembershipAddress membershipAddress, Membership.MembershipIsValid membershipIsValid, Membership.MembershipIsCorp membershipIsCorp) {
        return membershipRepository.save(
            new MembershipJpaEntity(
                    membershipName.getNameValue(),
                    membershipEmail.getEmailValue(),
                    membershipAddress.getAddressValue(),
                    membershipIsValid.isValidValue(),
                    membershipIsCorp.isCorpValue()
            )
        );
    }
}
