package com.eren.esmahan.peopledbweb.model;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class PersonTest {

    @Test
    public void canParse(){
         String csvLine="206270,Mr.,Burton,B,Lowry,M,burton.lowry@comcast.net,Oren Lowry,Fredda Lowry,Goodwyn,9/18/1987,10:33:51 PM,32.98,50,10/29/2011,Q4,H2,2011,10,October,Oct,29,Saturday,Sat,8.85,181592,26%,618-87-1917,215-747-2870,Palmerton,Carbon,Palmerton,PA,18071,Northeast,bblowry";
         Person person=Person.parse(csvLine);
         assertThat(person.getDob()).isEqualTo(LocalDate.of(1987,9,18));
    }

}