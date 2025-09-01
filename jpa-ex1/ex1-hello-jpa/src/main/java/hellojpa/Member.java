package hellojpa;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.*;

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

    // 기간
    @Embedded
    private Period workPeriod;

    // 개인 주소
    @Embedded
    private Address homeAddress;

    // 직장 주소
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "city",
                    column = @Column(name = "WORK_CITY")),
            @AttributeOverride(name = "street",
                    column = @Column(name = "WORK_STREET")),
            @AttributeOverride(name = "zipcode",
            column = @Column(name = "WORK_ZIPCODE"))
    })
    private Address workAddress;

    // 값 타입 매핑
    @ElementCollection
    @Column(name = "FOOD_NAME")
    @CollectionTable(name = "FAVORITE_FOOD", joinColumns = @JoinColumn(name = "MEMBER_ID"))
    private Set<String> favoriteFoods = new HashSet<>();

//    @ElementCollection
//    @CollectionTable(name = "ADDRESS_HISTORY", joinColumns = @JoinColumn(name = "MEMBER_ID"))
//    private List<Address> addressHistory = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "MEMBER_ID")
    private List<AddressEntity> addressHistory =  new ArrayList<>();

    // 다대일 매핑
    @ManyToOne(fetch = FetchType.LAZY)      // 지연 로딩
//    @ManyToOne(fetch = FetchType.EAGER)     // 즉시 로딩
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

    public Period getWorkPeriod() {
        return workPeriod;
    }

    public void setWorkPeriod(Period workPeriod) {
        this.workPeriod = workPeriod;
    }

    public Address getHomeAddress() {
        return homeAddress;
    }

    public void setHomeAddress(Address homeAddress) {
        this.homeAddress = homeAddress;
    }

    public Address getWorkAddress() {
        return workAddress;
    }

    public void setWorkAddress(Address workAddress) {
        this.workAddress = workAddress;
    }

    public Set<String> getFavoriteFoods() {
        return favoriteFoods;
    }

    public void setFavoriteFoods(Set<String> favoriteFoods) {
        this.favoriteFoods = favoriteFoods;
    }

    public List<AddressEntity> getAddressHistory() {
        return addressHistory;
    }

    public void setAddressHistory(List<AddressEntity> addressHistory) {
        this.addressHistory = addressHistory;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Locker getLocker() {
        return locker;
    }

    public void setLocker(Locker locker) {
        this.locker = locker;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public List<MemberProduct> getMemberProducts() {
        return memberProducts;
    }

    public void setMemberProducts(List<MemberProduct> memberProducts) {
        this.memberProducts = memberProducts;
    }
}