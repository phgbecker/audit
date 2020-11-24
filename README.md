# Audit<sup>+</sup>
Sistema para auditoria de guias médicas

## Como executar?

### Configurar variáveis de ambiente
- MySQL
    - MYSQL_HOST
    - MYSQL_PORT
    - MYSQL_SCHEMA
    - MYSQL_USUARIO
    - MYSQL_SENHA
- AWS
    - AWS_ACCESS_KEY_ID
    - AWS_SECRET_ACCESS_KEY
    - AWS_REGION
- AWS S3
    - AWS_S3_BUCKET
    - AWS_S3_BASE_HISTORICA
    - AWS_S3_BASE_TREINAMENTO
- AWS SageMaker
    - AWS_SAGEMAKER_ROLE_ARN
    - AWS_SAGEMAKER_IMAGEM
    - AWS_SAGEMAKER_NAMESPACE
    
### Iniciar aplicação
```mvn spring-boot:run```