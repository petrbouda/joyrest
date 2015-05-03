# Joyrest - Generated Documentation

### GET  /feeds/{id}/entries
**Produces:**

* application/json
* application/xml

**Response Class:**
```json
{
  "type" : "array",
  "items" : {
    "type" : "object",
    "id" : "urn:jsonschema:org:joyrest:examples:combiner:model:FeedEntry",
    "properties" : {
      "link" : {
        "type" : "string"
      },
      "publishDate" : {
        "type" : "integer",
        "format" : "UTC_MILLISEC"
      },
      "description" : {
        "type" : "string"
      },
      "title" : {
        "type" : "string"
      }
    }
  }
}
```

### GET  /feeds/{id}/combined
**Produces:**

* application/json
* application/xml

**Response Class:**
```json
{
  "type" : "object",
  "id" : "urn:jsonschema:org:joyrest:examples:combiner:model:CombinedFeed",
  "properties" : {
    "urls" : {
      "type" : "array",
      "items" : {
        "type" : "string"
      }
    },
    "refreshPeriod" : {
      "type" : "integer"
    },
    "description" : {
      "type" : "string"
    },
    "id" : {
      "type" : "string"
    },
    "feedEntries" : {
      "type" : "array",
      "items" : {
        "type" : "object",
        "id" : "urn:jsonschema:org:joyrest:examples:combiner:model:FeedEntry",
        "properties" : {
          "link" : {
            "type" : "string"
          },
          "publishDate" : {
            "type" : "integer",
            "format" : "UTC_MILLISEC"
          },
          "description" : {
            "type" : "string"
          },
          "title" : {
            "type" : "string"
          }
        }
      }
    },
    "title" : {
      "type" : "string"
    }
  }
}
```

### POST  /feeds
**Consumes:**

* application/json

**Produces:**

* application/json

**Request Class:**
```json
{
  "type" : "object",
  "id" : "urn:jsonschema:org:joyrest:examples:combiner:model:CombinedFeed",
  "properties" : {
    "urls" : {
      "type" : "array",
      "items" : {
        "type" : "string"
      }
    },
    "refreshPeriod" : {
      "type" : "integer"
    },
    "description" : {
      "type" : "string"
    },
    "id" : {
      "type" : "string"
    },
    "feedEntries" : {
      "type" : "array",
      "items" : {
        "$ref" : "urn:jsonschema:org:joyrest:examples:combiner:model:FeedEntry"
      }
    },
    "title" : {
      "type" : "string"
    }
  }
}
```

**Response Class:**
```json
{
  "type" : "object",
  "id" : "urn:jsonschema:org:joyrest:examples:combiner:model:CombinedFeed",
  "properties" : {
    "urls" : {
      "type" : "array",
      "items" : {
        "type" : "string"
      }
    },
    "refreshPeriod" : {
      "type" : "integer"
    },
    "description" : {
      "type" : "string"
    },
    "id" : {
      "type" : "string"
    },
    "feedEntries" : {
      "type" : "array",
      "items" : {
        "$ref" : "urn:jsonschema:org:joyrest:examples:combiner:model:FeedEntry"
      }
    },
    "title" : {
      "type" : "string"
    }
  }
}
```

### DELETE  /feeds/{id}
