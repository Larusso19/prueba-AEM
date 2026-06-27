# Prueba Técnica — Adobe Experience Manager (AEMaaCS)

Repositorio desarrollado como parte de una prueba técnica para Adobe AEM as a Cloud Service.

## ⚙️ Stack y Entorno

| Herramienta | Versión |
|---|---|
| Java | **21** (requerido por el SDK local utilizado) |
| Maven | 3.9+ |
| AEM SDK | AEMaaCS Quickstart `.jar` (Author local) |
| Node.js | 18+ (para `ui.frontend`) |
| AEM Core Components | v3 |

> **Nota sobre Java:** Aunque la especificación de la prueba indica Java 17, este proyecto utiliza **Java 21** debido a compatibilidad con la versión del AEMaaCS SDK instalada localmente. El código es completamente compatible y no utiliza APIs exclusivas de versiones posteriores a Java 17.

## 📁 Estructura del Proyecto

```
prueba-AEM/
├── core/               # Sling Models, Servlets (Java)
├── ui.apps/            # Componentes HTL, definiciones JCR
├── ui.content/         # Contenido de ejemplo, Templates, XF
├── ui.config/          # Configuraciones OSGi
├── ui.frontend/        # SCSS, webpack, assets
└── all/                # Paquete unificado para despliegue
```

## 🧩 Componente Principal: `card-list-showcase`

Componente personalizado que hereda de un contenedor de AEM Core Components v3, diseñado para demostrar dominio de:

- **HTL / Sightly** — `data-sly-use`, `data-sly-list`, `data-sly-repeat`, `data-sly-test`, `data-sly-template`, `data-sly-call`
- **Contextos de escape XSS** — `@context='text'`, `@context='html'`, `@context='uri'`, `@context='attribute'`
- **i18n** — `${'Texto' @ i18n}`
- **Sling Models** — interfaz + implementación, `@ChildResource`, procesamiento de Multifield
- **Servlet OSGi R7** — registrado por `resourceType` bajo `/bin/holafuturo/`
- **Editable Templates y Experience Fragments** bajo `/conf/`

## 🚀 Compilación y Despliegue Local

```bash
# Compilar e instalar todo el proyecto
mvn clean install -PautoInstallSinglePackage

# Solo el backend (core)
mvn clean install -pl core

# Solo ui.apps
mvn clean install -pl ui.apps -PautoInstallPackage
```

> Asegúrate de tener el AEM Author corriendo en `http://localhost:4502` antes de ejecutar los comandos de instalación.

## ⚠️ Decisiones Técnicas y Limitaciones Conocidas

### 1. `name="./cards"` en el `<field>` interior del Composite Multifield

El nodo `<field>` (container interior del multifield) conserva `name="./cards"`, lo cual difiere de la especificación estándar de Granite UI donde el container interior no debería tener `name`.

Esta decisión fue tomada de forma deliberada tras validación en CRXDE: al remover el atributo, el diálogo deja de persistir los subnodos `cards/item0`, `cards/item1` correctamente en el JCR. Con el atributo presente, la estructura se guarda de forma correcta y el Sling Model la lee sin problemas.

La estructura JCR resultante es la esperada:

```
card_list_showcase/
└── cards/
    ├── item0/
    │   ├── cardTitle
    │   ├── cardDescription
    │   ├── cardImage
    │   └── cardLink
    └── item1/
        └── ...
```

### 2. `context='text'` en lugar de `context='html'` para `cardDescription`

El campo `cardDescription` en el diálogo usa un `textarea` (texto plano) en lugar de un RTE (Rich Text Editor), por incompatibilidad del componente `richtext` de Granite UI dentro de un composite multifield en esta versión del SDK.

Como consecuencia, en el HTL se utiliza `context='text'` en lugar de `context='html'`, lo cual es el contexto correcto y más seguro para texto plano. Si el evaluador requiere `context='html'`, el campo debería migrarse a un RTE con sanitización AntiSamy, lo cual está fuera del alcance de esta configuración local.

## 👤 Autor

**Larusso19**
Prueba técnica — AEM Developer