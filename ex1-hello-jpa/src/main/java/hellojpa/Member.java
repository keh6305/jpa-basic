package hellojpa;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@TableGenerator(name = "MEMBER_SEQ_GENERATOR", table = "MY_SEQUENCES", pkColumnValue = "MEMBER_SEQ", allocationSize = 1)
public class Member extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "MEMBER_ID")
    private Long id;

    @Column(nullable = false)
    private String name;

//    private Integer age;
//
//    @Column(unique = true, length = 100)
//    private String email;
//
//    @Enumerated(EnumType.STRING)
//    @Column(name = "role_type")
//    private RoleType roleType;
//
//    @Temporal(TemporalType.TIMESTAMP)
//    @Column(name = "created_date")
//    private Date createdDate;
//
//    @Temporal(TemporalType.TIMESTAMP)
//    @Column(name = "last_modified_date")
//    private Date lastModifiedDate;
//
//    @Lob
//    private String description;
//
//    @Transient
//    @Column(columnDefinition = "varchar(100) default 'EMPTY'")
//    private String temp;

//    @Column(name = "TEAM_ID")
//    private Long teamId;

    // 다대일 매핑
    @ManyToOne
    @JoinColumn(name = "TEAM_ID", insertable = false, updatable = false) // 일대다 양방향 매핑
    private Team team;

    // 일대일 매칭
    @OneToOne
    @JoinColumn(name = "LOCKER_ID")
    private Locker locker;

    // 다대다 매칭
    // 최대한 사용하지 말 것
    @ManyToMany
    @JoinTable(name = "MEMBER_PRODUCT")
    private List<Product> products = new ArrayList<>();

    // 다대다 매칭 -> 일대다 매칭으로 변경
    @OneToMany(mappedBy = "member")
    private List<MemberProduct> memberProducts = new ArrayList<>();

    public Member() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;

        // 양방향 연관 관계 주입
        team.getMembers().add(this);
    }

    public Locker getLocker() {
        return locker;
    }

    public void setLocker(Locker locker) {
        this.locker = locker;
    }

    @Override
    public String toString() {
        return "Member{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", team=" + team +
                '}';
    }
}