package ke.co.rafiki.fmis.security;

import ke.co.rafiki.fmis.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.PermissionEvaluator;

@Configuration
public class PermissionEvaluatorProvider {

    @Autowired
    private FarmRepository farmRepository;

    @Autowired
    private FarmAssetRepository farmAssetRepository;

    @Autowired
    private FarmAnimalRepository farmAnimalRepository;

    @Autowired
    private FarmCropRepository farmCropRepository;

    @Autowired
    private FarmConsumptionRepository farmConsumptionRepository;

    @Autowired
    private FarmProductionRepository farmProductionRepository;

    @Autowired
    private FarmSaleRepository farmSaleRepository;

    @Autowired
    private FarmVcaRepository farmVcaRepository;

    @Autowired
    private FarmActivityRepository farmActivityRepository;

    @Autowired
    private FarmActivityLogRepository farmActivityLogRepository;

    @Bean
    public PermissionEvaluator farmPermissionEvaluator() {
        return new CustomPermissionEvaluator<>(farmRepository);
    }

    @Bean
    public PermissionEvaluator farmAssetPermissionEvaluator() {
        return new CustomPermissionEvaluator<>(farmAssetRepository);
    }

    @Bean
    public PermissionEvaluator farmActivityPermissionEvaluator() {
        return new CustomPermissionEvaluator<>(farmActivityRepository);
    }

    @Bean
    public PermissionEvaluator farmActivityLogPermissionEvaluator() {
        return new CustomPermissionEvaluator<>(farmActivityLogRepository);
    }

    @Bean
    public PermissionEvaluator farmAnimalPermissionEvaluator() {
        return new CustomPermissionEvaluator<>(farmAnimalRepository);
    }

    @Bean
    public PermissionEvaluator farmCropPermissionEvaluator() {
        return new CustomPermissionEvaluator<>(farmCropRepository);
    }

    @Bean
    public PermissionEvaluator farmConsumptionPermissionEvaluator() {
        return new CustomPermissionEvaluator<>(farmConsumptionRepository);
    }

    @Bean
    public PermissionEvaluator farmProductionPermissionEvaluator() {
        return new CustomPermissionEvaluator<>(farmProductionRepository);
    }

    @Bean
    public PermissionEvaluator farmSalePermissionEvaluator() {
        return new CustomPermissionEvaluator<>(farmSaleRepository);
    }

    @Bean
    public PermissionEvaluator farmVcaPermissionEvaluator() {
        return new CustomPermissionEvaluator<>(farmVcaRepository);
    }
}
