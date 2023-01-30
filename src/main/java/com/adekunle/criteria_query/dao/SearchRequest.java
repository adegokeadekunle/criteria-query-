package com.adekunle.criteria_query.dao;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SearchRequest {
    private String firstName;
    private String lastName;
    private String email;
}
