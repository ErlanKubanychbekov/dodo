package Final.Project.dodo.utils;

public enum language {
    RU,
    EN,

    KG;
    
    public static language getLanguage(int ordinal) {
        switch (ordinal ) {
            case 1:
                return RU;
            case 2:
                return EN;
            case 3:
                return KG;
            default:
                return RU;
        }
        
    }
}
