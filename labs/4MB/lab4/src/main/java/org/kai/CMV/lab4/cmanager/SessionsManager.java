package org.kai.CMV.lab4.cmanager;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import org.hibernate.cfg.Configuration;
import org.kai.CMV.lab4.cmanager.session.ExtendedSession;
import org.kai.CMV.lab4.cmanager.session.GeneralSession;
import org.kai.CMV.lab4.mappings.orgelqueue.Car;
import org.kai.CMV.lab4.mappings.orgelqueue.Client;
import org.kai.CMV.lab4.mappings.orgelqueue.MarkHelper;
import org.kai.CMV.lab4.mappings.orgelqueue.Parking;
import org.kai.CMV.lab4.mappings.orgelqueue.Parking_place;
import org.kai.CMV.lab4.mappings.orgelqueue.Payment;
import org.kai.CMV.lab4.mappings.orgelqueue.Rate;

public class SessionsManager {

    public static final String SESSION_NAME_KEY = "session.name";

    public static final int HPREPAIR_SESSION = 1;

    public static final int ORGELQUEUE_SESSION = 2;

    private static int _type;

    private static GeneralSession _generalSession;

    private static HashMap<String, ExtendedSession> sessions;

    public static void initType(int type) {
        _type = type;

        _generalSession = new GeneralSession();
        sessions = new HashMap<String, ExtendedSession>();
    }

    public static void createSessions(
            ConfigurationsManager aConfigurationManager) throws IOException {
        for (Configuration configuration : aConfigurationManager
                .getConfigurations()) {
            String name = (String) configuration.getProperties().get(
                    SESSION_NAME_KEY);
            sessions.put(name, new ExtendedSession(configuration,
                    _generalSession));
        }
    }

    public static ExtendedSession getSession(String aName) {
        return sessions.get(aName);
    }

    public static String[] getSessionsNames() {
        return sessions.keySet().toArray(new String[0]);
    }

    public static String getName(ExtendedSession session) {
        Iterator<Entry<String, ExtendedSession>> iterator = sessions.entrySet()
                .iterator();
        while (iterator.hasNext()) {
            Entry<String, ExtendedSession> entry = iterator.next();
            if (entry.getValue() == session) {
                return entry.getKey();
            }
        }

        return null;
    }

    public static ExtendedSession[] getSessions() {
        return sessions.values().toArray(new ExtendedSession[0]);
    }

    public static GeneralSession getGeneralSession() {
        return _generalSession;
    }

    public static Class<?>[] getEntities() {
        switch (_type) {
        case HPREPAIR_SESSION: {
            return null;
            // return new Class<?>[] { Cars.class, Drivers.class };
        }
        case ORGELQUEUE_SESSION: {
            return new Class<?>[] { Client.class, Car.class,
                    Parking_place.class, Parking.class, Payment.class,
                    MarkHelper.class, Rate.class };
        }
        }
        return null;
    }
}
