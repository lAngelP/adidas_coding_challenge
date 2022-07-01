package net.langelp.subscriptionapi.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "campaign")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CampaignEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "campaign_id_generator")
    @SequenceGenerator(name = "campaign_id_generator", sequenceName = "campaign_id_sequence", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "creation_timestamp", nullable = false)
    @CreationTimestamp
    private Date creationTimestamp;

    @Column(name = "update_timestamp", nullable = false)
    @UpdateTimestamp
    private Date updateTimestamp;


    @Column(name = "title", nullable = false)
    private String title;

    @ManyToMany(mappedBy = "campaigns")
    @ToString.Exclude
    private Set<UserEntity> users = new HashSet<>();


}
