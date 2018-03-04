package satoshiyamamoto.tastewaf.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;

@Configuration
@EnableJpaAuditing
public class AuditingConfiguration {

    @Bean
    AuditorAwareImpl auditorAware() {
        return new AuditorAwareImpl();
    }

    public static class AuditorAwareImpl implements AuditorAware<String> {
        private String auditor = "";

        @Override
        public Optional<String> getCurrentAuditor() {
            return Optional.ofNullable(auditor);
        }
    }

}
