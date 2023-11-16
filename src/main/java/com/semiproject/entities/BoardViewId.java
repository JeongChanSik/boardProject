package com.semiproject.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // getter setter toString, equalsAndHashcode 합쳐놓은 것
@AllArgsConstructor @NoArgsConstructor
public class BoardViewId {
    private Long seq;

    private Integer uid;
}
