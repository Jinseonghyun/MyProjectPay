package com.backend.membership.adapter.in.web;

import com.backend.membership.application.port.in.FindMembershipCommand;
import com.backend.membership.application.port.in.FindMembershipUseCase;
import com.backend.membership.application.port.in.ModifyMembershipCommand;
import com.backend.membership.application.port.in.ModifyMembershipUseCase;
import com.backend.membership.domain.Membership;
import common.WebAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@WebAdapter
@RestController
@RequiredArgsConstructor
class ModifyMembershipController {

    private final ModifyMembershipUseCase modifyMembershipUseCase;

    @PostMapping(path = "/membership/modify")
    ResponseEntity<Membership> modifyMembershipByMemberId(@RequestBody ModifyMembershipRequest request){

        ModifyMembershipCommand command = ModifyMembershipCommand.builder()
                .membershipId(request.getMembershipId())
                .name(request.getName())
                .address(request.getAddress())
                .email(request.getEmail())
                .isValid(request.isValid())
                .isCorp(request.isCorp())
                .build();
        return ResponseEntity.ok(modifyMembershipUseCase.modifyMembership(command));
    }
}
