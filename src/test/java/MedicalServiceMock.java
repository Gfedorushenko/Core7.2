
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import ru.netology.patient.entity.BloodPressure;
import ru.netology.patient.entity.HealthInfo;
import ru.netology.patient.entity.PatientInfo;
import ru.netology.patient.repository.PatientInfoRepository;
import ru.netology.patient.service.alert.SendAlertService;
import ru.netology.patient.service.medical.MedicalServiceImpl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.stream.Stream;

public class MedicalServiceMock {

    @ParameterizedTest
    @MethodSource("press")
    public void testCheckBloodPressure(int high, int low, int count) {
        PatientInfoRepository patientInfoRepository = Mockito.mock(PatientInfoRepository.class);
        Mockito.when(patientInfoRepository.getById("fde496e7-dd9f-4bfc-87ac-08400480b00e"))
                .thenReturn(new PatientInfo("Иван", "Петров", LocalDate.of(1980, 11, 26),
                        new HealthInfo(new BigDecimal("36.65"), new BloodPressure(120, 80))));

        SendAlertService alertService = Mockito.mock(SendAlertService.class);

        MedicalServiceImpl medicalService = new MedicalServiceImpl(patientInfoRepository, alertService);
        medicalService.checkBloodPressure("fde496e7-dd9f-4bfc-87ac-08400480b00e", new BloodPressure(high, low));

        Mockito.verify(alertService, Mockito.times(count)).send("Warning, patient with id: null, need help");
    }

    private static Stream<Arguments> press() {
        return Stream.of(
                Arguments.of(120, 80, 0),
                Arguments.of(120, 70, 1),
                Arguments.of(100, 80, 1)
        );
    }

    @ParameterizedTest
    @MethodSource("temp")
    public void testCheckTemperature(String temp, int count) {
        PatientInfoRepository patientInfoRepository = Mockito.mock(PatientInfoRepository.class);
        Mockito.when(patientInfoRepository.getById("fde496e7-dd9f-4bfc-87ac-08400480b00e"))
                .thenReturn(new PatientInfo("Иван", "Петров", LocalDate.of(1980, 11, 26),
                        new HealthInfo(new BigDecimal("36.65"), new BloodPressure(120, 80))));

        SendAlertService alertService = Mockito.mock(SendAlertService.class);

        MedicalServiceImpl medicalService = new MedicalServiceImpl(patientInfoRepository, alertService);
        medicalService.checkTemperature("fde496e7-dd9f-4bfc-87ac-08400480b00e", new BigDecimal(temp));

        Mockito.verify(alertService, Mockito.times(count)).send("Warning, patient with id: null, need help");
    }

    private static Stream<Arguments> temp() {
        return Stream.of(
                Arguments.of("36.7", 0),
//                Arguments.of("39.9",            1),
                Arguments.of("35.0", 1)
        );
    }
}

