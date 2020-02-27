package com.alpha.test.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "TBL_CLIENTS")
public class Client {

    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "risk_profile")
    private String riskProfile;

    public Client() {
    }

    public Client(long id, String riskProfile) {
        this.id = id;
        this.riskProfile = riskProfile;
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRiskProfile() {
        return riskProfile;
    }

    public void setRiskProfile(String riskProfile) {
        if (riskProfile.equals(ClientConstants.LOW_RISK) || riskProfile.equals(ClientConstants.NORMAL_RISK) || riskProfile.equals(ClientConstants.HIGH_RISK)) {
            this.riskProfile = riskProfile;
        } else
            throw new RuntimeException("invalid risk profile");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return id == client.id &&
                riskProfile.equals(client.riskProfile);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, riskProfile);
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", riskProfile='" + riskProfile + '\'' +
                '}';
    }
}
