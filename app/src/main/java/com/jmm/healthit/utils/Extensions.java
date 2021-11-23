package com.jmm.healthit.utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Extensions {

    public static Set<String> fromListToSet(List<String> items){

        return new HashSet<>(items);
    }

    public static List<String> fromSetToList(Set<String> items){
        return new ArrayList<>(items);
    }
}
