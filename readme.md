# Ecommerce Hexagonale

Fonctionnalités existantes
- Créer un produit : POST /products
- Récupérer tous les produits : GET /products
- Récupérer un produit par ID : GET /products/{id}
- Mettre à jour un produit : PUT /products/{id}
- Supprimer un produit : DELETE /products/{id}

Architecture
- Hexagonale (Ports & Adapters)
  - Domaine (domain.model) : entités et logique métier (coeur).
  - Ports (domain.port.in / domain.port.out) : interfaces pour communiquer vers/depuis le domaine.
  - Adapters (adapter.in / adapter.out) :
    - adapter.in.web : contrôleurs REST exposant les ports entrants.
    - adapter.out.persistence : implémentations JPA / repositories (non listées ici mais prévues).
  - Cette séparation permet de remplacer les technologies d'infrastructure sans impacter le domaine.


Comment lancer :
- Clone du projet
- `docker compose up -d` (ou démarrer MariaDB)
- `mvn clean install`
- `mvn spring-boot:run`
- Accès Swagger : http://localhost:8080/api/swagger-ui.html


