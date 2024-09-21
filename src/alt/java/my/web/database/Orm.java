package my.web.database;

import my.web.Crack;

import java.util.List;

public interface Orm {

    void createCrack(Crack shot);

    List<Crack> getCracks(String shooter);

    void clearCracks(String shooter);
}
