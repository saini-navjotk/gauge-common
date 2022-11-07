package com.tcs.eas.api.tools.common.step;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.aaronhe.threetengson.LocalDateConverter;
import org.aaronhe.threetengson.OffsetDateTimeConverter;
import org.threeten.bp.LocalDate;
import org.threeten.bp.OffsetDateTime;

public final class GaugeGSonFactory {

    public Gson createInstance() {
        return new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateConverter())
                .registerTypeAdapter(OffsetDateTime.class, new OffsetDateTimeConverter())
                .registerTypeAdapterFactory(new ValidatorAdapterFactory())
                .registerTypeAdapterFactory(new EnumValidatorAdapterFactory())
                .create();
    }

    public static GaugeGSonFactory getInstance() {
        return new GaugeGSonFactory();
    }
}
