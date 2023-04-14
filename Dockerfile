FROM node:14

WORKDIR /app

COPY package*.json ./

RUN npm install && npm cache clean --force

COPY . .

EXPOSE 8087
EXPOSE 8088
EXPOSE 4000

CMD ["node", "index.js"]