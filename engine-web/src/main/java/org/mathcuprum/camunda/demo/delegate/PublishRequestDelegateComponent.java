package org.mathcuprum.camunda.demo.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class PublishRequestDelegateComponent implements JavaDelegate {

  private static final Logger LOGGER = LoggerFactory.getLogger(PublishRequestDelegateComponent.class);

  @Override
  public void execute(DelegateExecution execution) throws Exception {
    LOGGER.info("########################################");
    LOGGER.info("Publish request instance {}", execution);
    LOGGER.info("Publish request instance {}", execution.getVariables());
    LOGGER.info("########################################");
  }

}
