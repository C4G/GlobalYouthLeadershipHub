FROM python:3.13-slim

###
# This is not your final Dockerfile! Not even close!
# Delete it all and replace it with the real deal!
#

RUN mkdir /web && echo "Hello world, from GitHub Actions!" > /web/index.html

CMD ["python", "-m", "http.server", "80", "-d", "/web" ]
