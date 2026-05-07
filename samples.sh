### Tag Upload
curl -X POST http://localhost:8080/api/tags/upload \
     -H "Content-Type: application/json" \
     -d '{
           "epc": "E2801191200073B134D50123",
           "tid": "E2801105200073B134D50123",
           "timestamp": "2026-04-19T14:30:00",
           "antennaId": 1,
           "rssi": -55.5,
           "count": 1,
           "frequencyPoint": 920.5,
           "phase": 1.2,
           "readerId": "READER-001",
           "userData": "batch-45",
           "reserved": "N/A"
         }'

### Get Latest Tag
curl -X GET http://localhost:8080/api/tags/latest

### Get All Tags (with paging and filter)
curl -X GET "http://localhost:8080/api/tags?epc=E280&readerId=READER-001&page=0&size=10"
