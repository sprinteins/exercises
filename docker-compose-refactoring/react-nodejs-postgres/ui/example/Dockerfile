FROM node:latest as builder

WORKDIR '/app'

COPY . .

RUN npm run start

FROM nginx:latest

COPY --from=builder /app/disst /usr/share/nginx/html
COPY ./nginx.conf  /etc/nginx/

CMD ["nginx" , "-g", "daemon off;"];

EXPOSE 8080
