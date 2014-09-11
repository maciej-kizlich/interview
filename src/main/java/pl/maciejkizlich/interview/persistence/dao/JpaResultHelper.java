package pl.maciejkizlich.interview.persistence.dao;

import java.util.List;

import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;

public class JpaResultHelper {


        public static <T> T getSingleResultOrNull(Query query){
            List<?> results = query.getResultList();
                if (results.isEmpty()){
                    return null;
                }
                else if (results.size() == 1){
                    return (T) results.get(0);
                }

            throw new NonUniqueResultException();
        }
    }