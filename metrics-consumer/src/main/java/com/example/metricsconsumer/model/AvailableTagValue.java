package com.example.metricsconsumer.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "available_tag_value")
public class AvailableTagValue {
    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "ref_available_tag_value_id")
    private RefAvailableTagValue refAvailableTagValue;

    @JsonIgnore
    @Column(name = "available_tag_id")
    private Long availableTagId;
}
