package com.example.StoreWide.model;


import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GlobalResponse {
    public String status;
    public String description;
    public Object details;
}
