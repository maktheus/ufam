function [H, g] = highEnphasisFilt(a, b, f, type, D0, n, show)
% highEnphasisFilt  Aplica filtro de alta enfase no dominio da frequencia.
%
%   [H, g] = highEnphasisFilt(a, b, f, type, D0, n, show)
%
%   H_HFE = a + b * H_HP
%   onde H_HP e o filtro passa-alta de Butterworth de ordem n e corte D0.
%
%   Entradas:
%     a    - offset (escalar >= 0)
%     b    - multiplicador da componente passa-alta (escalar > 0)
%     f    - imagem de entrada (grayscale, double ou uint8)
%     type - 'butterworth' (unico tipo implementado, para extensao futura)
%     D0   - frequencia de corte (pixels)
%     n    - ordem do filtro Butterworth
%     show - true/false: exibe figuras 2D e 3D do filtro e resultado
%
%   Saidas:
%     H    - filtro HEF no dominio da frequencia (tamanho de f)
%     g    - imagem filtrada (escala 0-255, uint8)

  f = double(f);
  [rows, cols] = size(f);

  % Grade de frequencias centrada
  [U, V] = meshgrid(-(cols/2):(cols/2 - 1), -(rows/2):(rows/2 - 1));
  D = sqrt(U.^2 + V.^2);

  % Filtro passa-baixa de Butterworth
  H_LP = 1 ./ (1 + (D ./ D0).^(2*n));

  % Filtro passa-alta
  H_HP = 1 - H_LP;

  % Filtro de alta enfase
  H = a + b * H_HP;

  % Aplicacao no dominio da frequencia
  F = fftshift(fft2(f));
  G = H .* F;
  g_raw = real(ifft2(ifftshift(G)));

  % Normaliza para [0, 255]
  g_min = min(g_raw(:));
  g_max = max(g_raw(:));
  g = uint8(255 * (g_raw - g_min) / (g_max - g_min + eps));

  if show
    % --- Figura 1: filtro 2D e 3D ---
    figure('Name', 'High-Emphasis Filter');
    subplot(1, 2, 1);
    imagesc(H); colormap(gca, 'hot'); colorbar;
    title(sprintf('HEF 2D  a=%.1f  b=%.1f  D0=%g  n=%d', a, b, D0, n));
    axis image off;

    subplot(1, 2, 2);
    step = max(1, floor(min(rows, cols) / 64));
    ui = -(rows/2):step:(rows/2);
    vi = -(cols/2):step:(cols/2);
    [Ui, Vi] = meshgrid(vi, ui);
    Di = sqrt(Ui.^2 + Vi.^2);
    Hi = a + b * (1 - 1 ./ (1 + (Di ./ D0).^(2*n)));
    surf(Ui, Vi, Hi, 'EdgeColor', 'none');
    colormap(gca, 'viridis'); colorbar;
    xlabel('u'); ylabel('v'); zlabel('H(u,v)');
    title(sprintf('HEF 3D  a=%.1f  b=%.1f  D0=%g  n=%d', a, b, D0, n));
    view([-35, 35]);

    % --- Figura 2: imagem original e filtrada ---
    figure('Name', 'Resultado HEF');
    subplot(1, 2, 1);
    imshow(uint8(f), []); title('Imagem original');
    subplot(1, 2, 2);
    imshow(g, []); title(sprintf('Apos HEF (a=%.1f, b=%.1f)', a, b));
  end
end
