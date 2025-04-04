package enums;

public enum Designation {
    
    SCHOOL_STAFF,
    BSED_FACULTY,
    BSNED_FACULTY,
    IT_FACULTY,
    BTVTED_FACULTY,
    BEED_FACULTY,
    BECED_FACULTY,
    BSABE_FACULTY,
    SOM_FACULTY,
    BSIT_1IT,
    BSIT_2IT,
    BSIT_3IT,
    BSIT_4IT,
    BEED_BEED1,
    BEED_BEED2,
    BEED_BEED3,
    BEED_BEED4,
    BECED_ECED1,
    BECED_ECED2,
    BECED_ECED3,
    BECED_ECED4,
    BSNED_SNED1,
    BSNED_SNED2,
    BSNED_SNED3,
    BSNED_SNED4,
    BSEDMATH_1SM,
    BSEDMATH_2SM,
    BSEDMATH_3SM,
    BSEDMATH_4SM,
    BSEDFILIPINO_1SF,
    BSEDFILIPINO_2SF,
    BSEDFILIPINO_3SF,
    BSEDFILIPINO_4SF,
    BSEDENGLISH_1SE,
    BSEDENGLISH_2SE,
    BSEDENGLISH_3SE,
    BSEDENGLISH_4SE,
    BTVTED_1STV1,
    BTVTED_1STV2,
    BTVTED_2STV1,
    BTVTED_2STV2,
    BTVTED_3STV1,
    BTVTED_3STV2,
    BTVTED_4STV1,
    BSABE_1A,
    BSABE_1B,
    BSABE_2A,
    BSABE_2B,
    BSABE_3A,
    BSABE_3B,
    SOM_MEDSCI1,
    SOM_MEDSCI2,
    SOM_MEDSCI3;

    public static Designation from(String string) throws RuntimeException{

        String designation = string.replace("-","_");

        return valueOf(designation.toUpperCase());

    }
    
    
}

