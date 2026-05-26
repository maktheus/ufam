function [g, T, iters] = globalThresh(f, detT)
% globalThresh  Limiarizacao global iterativa (Gonzalez & Woods).
%
%   [g, T, iters] = globalThresh(f, detT)
%
%   Algoritmo:
%     1. Escala f para [0,1]
%     2. T0 = media de f
%     3. Segmenta: G1 = pixels > T, G2 = pixels <= T
%     4. m1 = media(G1), m2 = media(G2)
%     5. T_novo = (m1 + m2) / 2
%     6. Se |T_novo - T| < detT, para; senao T = T_novo e volta ao 3
%     7. Retorna imagem binaria g = (f_scaled > T)
%
%   Entradas:
%     f    - imagem de entrada (qualquer tipo numerico)
%     detT - criterio de parada (default: 0.01)
%
%   Saidas:
%     g     - imagem binaria (logical)
%     T     - limiar final convergido
%     iters - numero de iteracoes ate convergencia

  if nargin < 2
    detT = 0.01;
  end

  % Escala para [0, 1]
  f = double(f);
  f_min = min(f(:));
  f_max = max(f(:));
  if f_max == f_min
    g = false(size(f));
    T = 0;
    iters = 0;
    return;
  end
  f_scaled = (f - f_min) / (f_max - f_min);

  % Estimativa inicial: media global
  T = mean(f_scaled(:));

  iters = 0;
  while true
    iters = iters + 1;

    % Segmentacao
    G1 = f_scaled(f_scaled >  T);
    G2 = f_scaled(f_scaled <= T);

    % Medias (protege divisao por zero se um grupo estiver vazio)
    if isempty(G1)
      m1 = 1;
    else
      m1 = mean(G1);
    end
    if isempty(G2)
      m2 = 0;
    else
      m2 = mean(G2);
    end

    T_new = (m1 + m2) / 2;

    if abs(T_new - T) < detT
      T = T_new;
      break;
    end
    T = T_new;
  end

  g = f_scaled > T;
end
