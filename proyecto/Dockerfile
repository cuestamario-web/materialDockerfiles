FROM alpine:3.20

ARG PROYECTO=node

ENV PROYECTO=$PROYECTO \
    APP_NOMBRE="SAMUEL"\
    APP_ENTORNO=desarrollo \
    APP_COLOR=verde\
    FORCE_COLOR=1

WORKDIR /app

RUN if [ "$PROYECTO" = "node" ]; then \
      apk add --no-cache nodejs npm; \
    elif [ "$PROYECTO" = "python" ]; then \
      apk add --no-cache python3; \
    else \
        echo "ERROR:El proyecto seleccionado no es permitido por favor cambie el nombre del proyecto por node o python "; exit 1; \
    fi

COPY $PROYECTO/ /app/

RUN if [ "$PROYECTO" = "node" ]; then \
        npm install --omit=dev; \
    else \
    python3 -m venv /app/.venv && \
    /app/.venv/bin/pip install --no-cache-dir -r requirements.txt;\
    fi

ENV PATH="/app/.venv/bin:$PATH"

CMD sh -c "if [ \"$PROYECTO\" = \"node\" ]; then node app.js; else python app.py; fi"