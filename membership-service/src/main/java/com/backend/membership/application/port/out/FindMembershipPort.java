package com.backend.membership.application.port.out;

import com.backend.membership.adapter.out.persistence.MembershipJpaEntity;
import com.backend.membership.domain.Membership;

public interface FindMembershipPort {

    MembershipJpaEntity findMembership(
            Membership.MembershipId membershipId
    );
}
