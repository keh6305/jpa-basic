package hellojpql;

import jakarta.persistence.*;

@Entity
public class Member {
    @Id
    @GeneratedValue
    private int id;

    private String name;
    private int age;

    @ManyToOne
    @JoinColumn(name = "TEAM_ID")
    private Team team;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }
}