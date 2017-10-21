package com.handicraft.core.dto.Recycles;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "Recycle")
@Table(name = "recycle")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(value = { AuditingEntityListener.class })
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Recycle implements Serializable{

    private static final long serialVersionUID = -7317915570489727833L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long rid;

    private String company;

    private String address;

    private String phone;

}
