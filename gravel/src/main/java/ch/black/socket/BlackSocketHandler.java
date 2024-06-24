package ch.black.socket;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Logger;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class BlackSocketHandler extends TextWebSocketHandler {
    private Logger logger = Logger.getLogger(getClass().getName());

    private final List<WebSocketSession> sessions = new CopyOnWriteArrayList();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.add(session);
        super.afterConnectionEstablished(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessions.remove(session);
        super.afterConnectionClosed(session, status);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        super.handleTextMessage(session, message);
        sessions.forEach(webSocketSession -> {
            try {
                TimeZone tz = TimeZone.getTimeZone("CET");
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH.SSS:mmXXX");
                df.setTimeZone(tz);
                String nowAsISO = df.format(new Date());
                TextMessage output = new TextMessage(nowAsISO + ": " + message.getPayload());
                webSocketSession.sendMessage(output);
            } catch (IOException e) {
                logger.warning(e.getMessage());
            }
        });
    }
}
