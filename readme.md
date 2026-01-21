# Ecommerce Hexagonale

## Fonctionnalités

### Produits (Products)
- Créer un produit : `POST /api/products`
- Récupérer tous les produits : `GET /api/products`
- Récupérer un produit par ID : `GET /api/products/{id}`
- Mettre à jour un produit : `PUT /api/products/{id}`
- Supprimer un produit : `DELETE /api/products/{id}`

### Panier (Carts)
- Récupérer le panier : `GET /api/carts/{customerId}`
- Ajouter un article au panier : `POST /api/carts/{customerId}/items?productId={productId}&quantity={quantity}`
- Mettre à jour la quantité d'un article : `PUT /api/carts/{customerId}/items?productId={productId}&quantity={quantity}`
- Supprimer un article du panier : `DELETE /api/carts/{customerId}/items/{productId}`
- Appliquer un code promo : `POST /api/carts/{customerId}/discount?code={code}`

## Architecture
- Hexagonale (Ports & Adapters)
  - Domaine (domain.model) : entités et logique métier (coeur).
  - Ports (domain.port.in / domain.port.out) : interfaces pour communiquer vers/depuis le domaine.
  - Adapters (adapter.in / adapter.out) :
    - adapter.in.web : contrôleurs REST exposant les ports entrants.
    - adapter.out.persistence : implémentations JPA / repositories (non listées ici mais prévues).
  - Cette séparation permet de remplacer les technologies d'infrastructure sans impacter le domaine.

## Test unitaires
  - `ProductServiceTest.java`

## Comment lancer
- Clone du projet
- `docker compose up -d` (ou démarrer MariaDB)
- `mvn clean install`
- `mvn spring-boot:run`
- Accès Swagger : `http://localhost:8080/api/swagger-ui.html`

## Status
Project in progress
