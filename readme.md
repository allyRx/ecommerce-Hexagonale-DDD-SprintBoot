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

### Commandes (Orders)
- Créer une commande : `POST /order` (avec customerId et shippingAddress)
- Récupérer une commande par ID : `GET /order/{orderId}`
- Récupérer les commandes d'un client : `GET /order/customer/{customerId}`
- Annuler une commande : `POST /order/{orderId}/cancel`

## Architecture
- Hexagonale (Ports & Adapters)
  - Domaine (domain.model) : entités et logique métier (coeur).
  - Ports (domain.port.in / domain.port.out) : interfaces pour communiquer vers/depuis le domaine.
  - Adapters (adapter.in / adapter.out) :
    - adapter.in.web : contrôleurs REST exposant les ports entrants.
    - adapter.out.persistence : implémentations JPA / repositories (non listées ici mais prévues).
  - Cette séparation permet de remplacer les technologies d'infrastructure sans impacter le domaine.

## Test unitaires
  - `ProductServiceTest.java` : Tests des opérations CRUD produits (création, lecture, mise à jour, suppression)

## Comment lancer
- Clone du projet
- `docker compose up -d` (ou démarrer MariaDB)
- `mvn clean install`
- `mvn spring-boot:run`
- Accès Swagger UI : `http://localhost:8080/swagger-ui.html`

## Documentation API
La documentation complète des API (Products, Carts, Orders) est disponible via Swagger UI :
- **URL** : `http://localhost:8080/swagger-ui.html`
- **OpenAPI JSON** : `http://localhost:8080/v3/api-docs`

## Status
Project in progress
