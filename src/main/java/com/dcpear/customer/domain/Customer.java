package com.dcpear.customer.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;


@Table(name="customer")
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Customer {
    private @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Basic(optional = false)
    @Column (nullable = false)
    private String firstName;

    @Basic(optional = false)
    @Column(nullable = false)
    private String lastName;

    // generates "yyyy-MM-dd" output
    @JsonSerialize(using = ToStringSerializer.class)
// handles "yyyy-MM-dd" input just fine (note: "yyyy-M-d" format will not work)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @Column
    private LocalDate dob;

    @Basic(optional = false)
    @Column(nullable = false)
    private String email;

    @Column
    private String phone;

    @Basic(optional = false)
    @Column(nullable = false)
    private String address;

    @Column
    @Enumerated(EnumType.STRING)
    private Level level;

    long getAge() {
        return ChronoUnit.YEARS.between(dob, LocalDate.now());
    }

    String getDisplayName() {
        return firstName + " " + lastName;
    }

}
