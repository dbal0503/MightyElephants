package dev.mightyelephants.backend;

import dev.mightyelephants.backend.model.DeliveryMan;
import dev.mightyelephants.backend.model.OfficeLocation;
import dev.mightyelephants.backend.repository.DeliveryManRepository;
import dev.mightyelephants.backend.repository.OfficeLocationRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    private final OfficeLocationRepository officeLocationRepository;

    private final DeliveryManRepository deliveryManRepository;

    public DataInitializer(OfficeLocationRepository officeLocationRepository, DeliveryManRepository deliveryManRepository) {
        this.officeLocationRepository = officeLocationRepository;
        this.deliveryManRepository = deliveryManRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        List<OfficeLocation> officeLocations = Arrays.asList(
                //Quebec Locations
                new OfficeLocation("Montreal Office", "1250 Rue Guy, Montreal, QC H3H 2L3", 45.5017, -73.5673),
                new OfficeLocation("Quebec City Office", "2800 Boulevard Laurier, Quebec City, QC G1V 0C1", 46.8139, -71.2082),
                new OfficeLocation("Laval Office", "3035 Boulevard Le Carrefour, Laval, QC H7T 1C8", 45.6066, -73.7124),
                new OfficeLocation("Gatineau Office", "15 Rue Victoria, Gatineau, QC J8X 2A1", 45.4765, -75.7013),
                new OfficeLocation("Longueuil Office", "777 Rue Saint-Laurent O, Longueuil, QC J4K 5E9", 45.5312, -73.5181),
                new OfficeLocation("Sherbrooke Office", "375 Rue King O, Sherbrooke, QC J1H 1R4", 45.4042, -71.8929),
                new OfficeLocation("Saguenay Office", "950 Boulevard Talbot, Saguenay, QC G7H 4B3", 48.4283, -71.0681),
                new OfficeLocation("Trois-Rivières Office", "4125 Rue Jacques-de-Labadie, Trois-Rivières, QC G9A 5C9", 46.3453, -72.5477),
                new OfficeLocation("Terrebonne Office", "950 Montée des Pionniers, Terrebonne, QC J6V 1S8", 45.7050, -73.6191),
                new OfficeLocation("Saint-Jean-sur-Richelieu Office", "160 Rue Saint-Jean-Baptiste, Saint-Jean-sur-Richelieu, QC J3B 1W9", 45.3061, -73.2540),
                new OfficeLocation("Repentigny Office", "333 Boulevard Brien, Repentigny, QC J6A 4R8", 45.7420, -73.4513),
                new OfficeLocation("Brossard Office", "8000 Boulevard du Quartier, Brossard, QC J4Y 0E5", 45.4703, -73.4511),
                new OfficeLocation("Drummondville Office", "755 Boulevard René-Lévesque, Drummondville, QC J2C 6Y7", 45.8800, -72.4919),
                new OfficeLocation("Saint-Jérôme Office", "900 Boulevard Grignon, Saint-Jérôme, QC J7Y 3S7", 45.7808, -74.0039),
                new OfficeLocation("Granby Office", "40 Rue Principale, Granby, QC J2G 2T4", 45.4032, -72.7344),
                new OfficeLocation("Blainville Office", "1345 Boulevard Michèle-Bohec, Blainville, QC J7C 5L4", 45.6742, -73.8825),
                new OfficeLocation("Dollard-Des Ormeaux Office", "3829 Boulevard Saint-Jean, Dollard-Des Ormeaux, QC H9G 1X2", 45.4930, -73.8204),
                new OfficeLocation("Mirabel Office", "16960 Rue de la Paix, Mirabel, QC J7J 2V8", 45.6519, -74.0808),
                new OfficeLocation("Châteauguay Office", "200 Boulevard d'Anjou, Châteauguay, QC J6J 2R3", 45.3610, -73.7495),
                new OfficeLocation("Saint-Hyacinthe Office", "900 Rue Johnson, Saint-Hyacinthe, QC J2S 5T8", 45.6304, -72.9568),
                new OfficeLocation("Shawinigan Office", "5400 Boulevard Royal, Shawinigan, QC G9N 4R5", 46.5667, -72.7500),
                new OfficeLocation("Joliette Office", "1075 Rue Firestone, Joliette, QC J6E 6X6", 46.0217, -73.4526),
                new OfficeLocation("Victoriaville Office", "625 Boulevard Jutras E, Victoriaville, QC G6P 4T6", 46.0570, -71.9589),
                new OfficeLocation("Val-d'Or Office", "1010 3e Avenue, Val-d'Or, QC J9P 1T4", 48.1000, -77.7833),
                new OfficeLocation("Rimouski Office", "419 Avenue Léonidas S, Rimouski, QC G5L 2T5", 48.4488, -68.5231),

// Other Canadian locations
                new OfficeLocation("Toronto Office", "150 King St W, Toronto, ON M5H 1J9", 43.6510, -79.3470),
                new OfficeLocation("Vancouver Office", "200 Burrard St, Vancouver, BC V6C 3L6", 49.2827, -123.1207),
                new OfficeLocation("Calgary Office", "707 5 St SW, Calgary, AB T2P 0Y3", 51.0447, -114.0719),
                new OfficeLocation("Edmonton Office", "10180 101 St NW, Edmonton, AB T5J 3S4", 53.5461, -113.4938),
                new OfficeLocation("Winnipeg Office", "360 Main St, Winnipeg, MB R3C 3Z3", 49.8951, -97.1384),
                new OfficeLocation("Saskatoon Office", "111 2nd Ave S, Saskatoon, SK S7K 1K6", 52.1332, -106.6700),
                new OfficeLocation("Regina Office", "2201 11th Ave, Regina, SK S4P 0J8", 50.4452, -104.6189),
                new OfficeLocation("Ottawa Office", "110 Laurier Ave W, Ottawa, ON K1P 1J1", 45.4215, -75.6972),
                new OfficeLocation("Halifax Office", "1801 Hollis St, Halifax, NS B3J 3C8", 44.6488, -63.5752),
                new OfficeLocation("St. John's Office", "235 Water St, St. John's, NL A1C 1B6", 47.5615, -52.7126),
                new OfficeLocation("Charlottetown Office", "82 Fitzroy St, Charlottetown, PE C1A 1R5", 46.2382, -63.1311),
                new OfficeLocation("Fredericton Office", "125 Regent St, Fredericton, NB E3B 3W6", 45.9636, -66.6431),
                new OfficeLocation("Whitehorse Office", "2121 2nd Ave, Whitehorse, YT Y1A 1C1", 60.7212, -135.0568),
                new OfficeLocation("Yellowknife Office", "4908 49 St, Yellowknife, NT X1A 2N9", 62.4540, -114.3718),
                new OfficeLocation("Iqaluit Office", "Building 2275, Iqaluit, NU X0A 0H0", 63.7467, -68.5168)

        );


        officeLocationRepository.saveAll(officeLocations);

        for (OfficeLocation location : officeLocations) {
            DeliveryMan driver1 = new DeliveryMan(1L, location.getAddress(), 50, "Standard");

            DeliveryMan driver2 = new DeliveryMan(2L, location.getAddress(), 100, "Express");

            deliveryManRepository.saveAll(Arrays.asList(driver1, driver2));
        }
    }
}
