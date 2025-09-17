package com.jpadata.datajpa.repository;

import com.jpadata.datajpa.dto.MemberDto;
import com.jpadata.datajpa.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
//    @Query(name = "Member.findByUsername")
    List<Member> findByUsername(@Param("username") String username);

    List<Member> findByUsernameAndAgeGreaterThan(String username, Integer age);

    List<Member> findTop3By();

    @Query("SELECT m FROM Member m WHERE m.username = :username AND m.age = :age ")
    List<Member> findUser(@Param("username") String username, @Param("age") int age);

    @Query("SELECT m.username FROM Member m ")
    List<String> findUsernameList();

    @Query("SELECT new com.jpadata.datajpa.dto.MemberDto(m.id, m.username, t.name) FROM Member m JOIN m.team t ")
    List<MemberDto> findMemberDto();

    @Query("SELECT m FROM Member m WHERE m.username IN :names ")
    List<Member> findByNames(@Param("names") Collection<String> names);

    List<Member> findListByUsername(String username);
    Member findMemberByUsername(String username);
    Optional<Member> findOptionalByUsername(String username);

    @Query(value = "SELECT m FROM Member m LEFT JOIN m.team t ", countQuery = "SELECT COUNT(m.username) FROM Member m ")
    Page<Member> findPageByAge(int age, Pageable pageable);
    Slice<Member> findSliceByAge(int age, Pageable pageable);
}