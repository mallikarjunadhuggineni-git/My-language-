package com.ruralbanking.chatbot.service;

import com.ruralbanking.chatbot.dto.ChatRequest;
import com.ruralbanking.chatbot.dto.ChatResponse;
import com.ruralbanking.chatbot.entity.Conversation;
import com.ruralbanking.chatbot.repository.ConversationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;

@Service
public class ChatService {

    private final ConversationRepository repository;

    public ChatService(ConversationRepository repository) {
        this.repository = repository;
    }

    public ChatResponse chat(ChatRequest request) {
        String language = normalizeLanguage(request.language());
        String response = buildBankingResponse(language, request.message().trim());

        Conversation saved = repository.save(
                new Conversation(language, request.message().trim(), response)
        );

        return toResponse(saved);
    }

    public List<ChatResponse> history() {
        return repository.findAllByOrderByCreatedAtDesc()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public void clearHistory() {
        repository.deleteAllInBatch();
    }

    private String normalizeLanguage(String language) {
        return switch (language.trim().toLowerCase(Locale.ROOT)) {
            case "telugu" -> "Telugu";
            case "hindi" -> "Hindi";
            case "english" -> "English";
            default -> throw new IllegalArgumentException(
                    "Unsupported language. Choose English, Telugu or Hindi."
            );
        };
    }

    private String buildBankingResponse(String language, String question) {
        String q = question.toLowerCase(Locale.ROOT);
        String answer;

        if (containsAny(q, "balance", "account balance", "బ్యాలెన్స్", "खाता शेष")) {
            answer = switch (language) {
                case "Telugu" -> "మీ ఖాతా బ్యాలెన్స్‌ను ATM, మొబైల్ బ్యాంకింగ్ లేదా బ్యాంక్ బ్రాంచ్ ద్వారా చెక్ చేసుకోవచ్చు.";
                case "Hindi" -> "आप अपना खाता बैलेंस ATM, मोबाइल बैंकिंग या बैंक शाखा के माध्यम से चेक कर सकते हैं।";
                default -> "You can check your account balance using an ATM, mobile banking, or your bank branch.";
            };
        } else if (containsAny(q, "loan", "లాన్", "ఋణం", "ऋण", "लोन")) {
            answer = switch (language) {
                case "Telugu" -> "లోన్ కోసం మీ బ్యాంక్ బ్రాంచ్‌ను సంప్రదించి అర్హత, వడ్డీ రేటు మరియు అవసరమైన పత్రాల గురించి తెలుసుకోండి.";
                case "Hindi" -> "लोन के लिए अपनी बैंक शाखा से संपर्क करें और पात्रता, ब्याज दर तथा आवश्यक दस्तावेजों की जानकारी लें।";
                default -> "For a loan, contact your bank branch to ask about eligibility, interest rates, and required documents.";
            };
        } else if (containsAny(q, "atm", "cash", "ఏటీఎం", "एटीएम")) {
            answer = switch (language) {
                case "Telugu" -> "ATMలో నగదు తీసుకోవడానికి మీ డెబిట్ కార్డ్ మరియు PIN అవసరం. PINను ఎవరితోనూ పంచుకోకండి.";
                case "Hindi" -> "ATM से पैसे निकालने के लिए डेबिट कार्ड और PIN की आवश्यकता होती है। अपना PIN किसी के साथ साझा न करें।";
                default -> "To withdraw cash from an ATM, use your debit card and PIN. Never share your PIN with anyone.";
            };
        } else if (containsAny(q, "upi", "payment", "digital payment", "యూపీఐ", "చెల్లింపు", "यूपीआई", "भुगतान")) {
            answer = switch (language) {
                case "Telugu" -> "UPI చెల్లింపులకు మీ బ్యాంక్ ఖాతాతో లింక్ అయిన UPI యాప్‌ను ఉపయోగించండి. UPI PINను ఎవరితోనూ పంచుకోకండి.";
                case "Hindi" -> "UPI भुगतान के लिए अपने बैंक खाते से जुड़े UPI ऐप का उपयोग करें। अपना UPI PIN किसी के साथ साझा न करें।";
                default -> "For UPI payments, use a UPI app linked to your bank account. Never share your UPI PIN with anyone.";
            };
        } else if (containsAny(q, "password", "pin", "పిన్", "పాస్‌వర్డ్", "पासवर्ड", "पिन")) {
            answer = switch (language) {
                case "Telugu" -> "మీ PIN లేదా పాస్‌వర్డ్‌ను ఎవరికీ చెప్పకండి. మర్చిపోయినట్లయితే బ్యాంక్ అధికారిక యాప్ లేదా బ్రాంచ్ ద్వారా రీసెట్ చేయండి.";
                case "Hindi" -> "अपना PIN या पासवर्ड किसी को न बताएं। भूल जाने पर बैंक के आधिकारिक ऐप या शाखा से इसे रीसेट करें।";
                default -> "Never share your PIN or password. If you forget it, reset it using your bank's official app or branch.";
            };
        } else {
            answer = switch (language) {
                case "Telugu" -> "మీ బ్యాంకింగ్ ప్రశ్నను మరింత వివరంగా అడగండి. మీరు ఖాతా, లోన్, ATM లేదా డిజిటల్ చెల్లింపుల గురించి అడగవచ్చు.";
                case "Hindi" -> "कृपया अपना बैंकिंग प्रश्न थोड़ा विस्तार से पूछें। आप खाता, लोन, ATM या डिजिटल भुगतान के बारे में पूछ सकते हैं।";
                default -> "Please ask your banking question in a little more detail. You can ask about accounts, loans, ATMs, or digital payments.";
            };
        }

        return answer;
    }

    private boolean containsAny(String text, String... values) {
        for (String value : values) {
            if (text.contains(value.toLowerCase(Locale.ROOT))) {
                return true;
            }
        }
        return false;
    }

    private ChatResponse toResponse(Conversation c) {
        return new ChatResponse(
                c.getId(),
                c.getLanguage(),
                c.getUserMessage(),
                c.getBotResponse(),
                c.getCreatedAt()
        );
    }
}
