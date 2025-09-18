package com.jpadata.datajpa.repository;

import com.jpadata.datajpa.entity.Member;

import java.util.List;

public interface MemberRepositoryCustom {
    List<Member> findMemberCustom();
}