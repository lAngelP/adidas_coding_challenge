package net.langelp.subscriptionapi.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "user_data")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {

    @Id
    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "creation_timestamp", nullable = false)
    @CreationTimestamp
    private Date creationTimestamp;

    @Column(name = "update_timestamp", nullable = false)
    @UpdateTimestamp
    private Date updateTimestamp;


    @Column(name = "name")
    private String name; //Optional parameter

    @Column(name = "gender")
    private String gender; //Optional parameter

    @Column(name = "birth_date", nullable = false)
    private Date birthDate;

    @Column(name = "rgpd_consent", nullable = false)
    private Boolean rgpdConsent;

    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "user_campaigns",
            joinColumns = { @JoinColumn(name = "user_id") },
            inverseJoinColumns = { @JoinColumn(name = "campaign_id") }
    )
    @ToString.Exclude
    Set<CampaignEntity> campaigns = new HashSet<>();

}
