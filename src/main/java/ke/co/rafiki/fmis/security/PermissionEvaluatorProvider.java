package ke.co.rafiki.fmis.security;

import ke.co.rafiki.fmis.repository.FarmAssetRepository;
import ke.co.rafiki.fmis.repository.FarmRepository;
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

    @Bean
    public PermissionEvaluator farmPermissionEvaluator() {
        return new CustomPermissionEvaluator<>(farmRepository);
    }

    @Bean
    public PermissionEvaluator farmAssetPermissionEvaluator() {
        return new CustomPermissionEvaluator<>(farmAssetRepository);
    }

}
