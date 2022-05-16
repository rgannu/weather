@org.hibernate.annotations.GenericGenerator(name = "pooled", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator", parameters = {
        @org.hibernate.annotations.Parameter(name = "sequence_name", value = "hibernate_sequence"),
        @org.hibernate.annotations.Parameter(name = "prefer_entity_table_as_segment_value", value = "true"),
        @org.hibernate.annotations.Parameter(name = "optimizer", value = "pooled-lo"),
        @org.hibernate.annotations.Parameter(name = "increment_size", value = "10")})
package com.utopian.weather.persistence.model;

