Регистрация
curl -v -H "Content-Type: application/json" -X POST -d  "@loginData.json" http://localhost:9999/api/v1/registration

Аутентификация, выдача токена
curl -v -H "Content-Type: application/json" -X POST -d  "@loginData.json" http://localhost:9999/api/v1/authentication

GET всех постов
curl -H "Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpZCI6MX0.fdPwJPbRZHI2cv23H5DktbDkm5rPE8_Zy1Crr2iCX6g" http://localhost:9999/api/v1/posts/recent

DELETE поста
curl -H "Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpZCI6MX0.fdPwJPbRZHI2cv23H5DktbDkm5rPE8_Zy1Crr2iCX6g" -v -X DELETE http://localhost:9999/api/v1/posts/2

GET определённого поста
curl -H "Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpZCI6MX0.fdPwJPbRZHI2cv23H5DktbDkm5rPE8_Zy1Crr2iCX6g" http://localhost:9999/api/v1/posts/1

CREATE поста
curl -H "Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpZCI6MX0.fdPwJPbRZHI2cv23H5DktbDkm5rPE8_Zy1Crr2iCX6g" -v -H "Content-Type: application/json" -X POST -d  "@postData.json" http://localhost:9999/api/v1/posts

UPDATE POST
curl -H "Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpZCI6MX0.fdPwJPbRZHI2cv23H5DktbDkm5rPE8_Zy1Crr2iCX6g" -H "Content-Type: application/json" -X PUT -d "@data.json"  http://localhost:9999/api/v1/posts/1

REPOST
curl -H "Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpZCI6MX0.fdPwJPbRZHI2cv23H5DktbDkm5rPE8_Zy1Crr2iCX6g" -v -H "Content-Type: application/json" -X POST -d  "@data.json" http://localhost:9999/api/v1/posts/repost/1
