# bueno-search

## Design thoughts
### Domain 
A device comprises multiple sensors

A sensor has its own attributes like name, type,etc...

```mermaid
classDiagram
      Animal --* Duck
      Animal : +int device_id
      Animal : +String device_name
      class Duck{
          +String sensor_name
          +String sensor_type
          +List sensor_tags
          +Date sensor_created_dt
          +Date sensor_updated_dt
          +String sensor_unit
          +Date sensor_reading_dt
          +Number sensor_reading_value
      }
```


### Assumptions
* Date fields are dealt with UTC, not considered for any timezones for simplicity

